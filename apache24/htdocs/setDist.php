<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$car_idx = $_POST['car_idx'];




header('content-type: text/html; charset=utf-8');



$query = "select ifnull(sum(distance),0)+ trvl_dstnc as distance, ifnull(date(max(date)),date(now())) as date from car left outer join drive_history on car.car_idx = drive_history.car_idx, refer
where car.car_idx = $car_idx and car.plnumber = refer.vhrno";



	
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