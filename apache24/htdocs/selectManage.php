<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$selected = $_POST['selected'];





header('content-type: text/html; charset=utf-8');



$query = "select * from maintain";

$cond = " ";
if(!empty($selected)){
  $cond = $cond." where main_type in($selected)";
}


$query = $query.$cond." order by main_type";


	
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