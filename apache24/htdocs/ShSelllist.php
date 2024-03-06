<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$brand = $_POST['brand'];
$model = $_POST['model'];
$condi = $_POST['cond'];




header('content-type: text/html; charset=utf-8');



$query = "select distinct($condi) as name from model as m left outer join fuel_connection as f on m.model_idx = f.model_idx 
left outer join fuel on f.fuel_idx = fuel.fuel_idx left outer join connection as c on m.model_idx = c.model_idx
 left outer join opt_grade as g on c.opt_grade_idx = g.opt_grade_idx where !isnull(m.model_idx) and c.opt_grade_idx != 0";

$cond = " ";
if(!empty($brand)){
  $cond = $cond." and brand='$brand'";
}

if(!empty($model)){
  $cond = $cond." and m.year='$model'";
}



$query = $query.$cond;


	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, $query);
$rowCnt= mysqli_num_rows($result);
$arr= array();

for($i=0;$i<$rowCnt;$i++){
      $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
        $arr[$i]= $row;      
}


  $jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo "$jsonData";

mysqli_close($con);
?>