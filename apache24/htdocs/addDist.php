<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$car_idx = $_POST['car_idx'];
$date = $_POST['date'];
$distance = $_POST['distance'];
$memo = $_POST['memo'];





header('content-type: text/html; charset=utf-8');



$query = "insert into drive_history (car_idx,date,distance,memo) values ($car_idx,date('$date'),
(select (select $distance -dist as distance from (select ifnull(sum(distance),0) + trvl_dstnc as dist 
from car left outer join drive_history on car.car_idx = drive_history.car_idx  ,refer where car.car_idx = $car_idx
and car.plnumber = refer.vhrno) as d)),'$memo')";



	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, $query);



mysqli_close($con);
?>