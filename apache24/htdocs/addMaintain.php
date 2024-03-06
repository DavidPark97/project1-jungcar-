<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$car_idx = $_POST['car_idx'];
$date = $_POST['date'];
$center_idx = $_POST['center_idx'];
$main_type = $_POST['main_type'];
$charge = $_POST['charge'];





header('content-type: text/html; charset=utf-8');



$query = "insert into maintain_history (car_idx,date,center_idx,charge,main_type) values ($car_idx,'$date',$center_idx,$charge,$main_type)";



	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, $query);



mysqli_close($con);
?>