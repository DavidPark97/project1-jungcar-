<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$car_idx = $_POST['car_idx'];
$year = $_POST['year'];
$month = $_POST['month'];




header('content-type: text/html; charset=utf-8');



$query = "select (select sum(distance)+trvl_dstnc from drive_history,refer,car where drive_history.date<=d.date and drive_history.car_idx=$car_idx and vhrno=plnumber and drive_history.car_idx = car.car_idx) as total, 
date,memo,drive_idx, distance from drive_history as d where car_idx = $car_idx and year(date)=$year and month(date)=$month order by date;";

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