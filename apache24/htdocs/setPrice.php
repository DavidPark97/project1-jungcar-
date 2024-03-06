<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

$start = $_POST['start'];
$end = $_POST['end'];
$vhrno = $_POST['vhrno'];
$distance = $_POST['distance'];




header('content-type: text/html; charset=utf-8');



$query = "select ifnull(round(avg(acqs_amount)/10000,0),0) as price, concat(cnm,' ',use_fuel_nm,' ',dspval,'cc') as name from refer
where regist_de between $start and $end and cnm = (select cnm from refer where vhrno='$vhrno') 
and trvl_dstnc between truncate($distance/3000,0)*3000 and (truncate($distance/3000,0)+1)*3000;";


	
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