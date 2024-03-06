<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$car_idx = $_POST['car_idx'];
$date = $_POST['date'];
$place = $_POST['place'];
$charge = $_POST['charge'];
$unit = $_POST['unit'];
$memo = $_POST['memo'];





header('content-type: text/html; charset=utf-8');



$query = "insert into oil_history (car_idx,date,charge,unit,memo,place) values ($car_idx,date('$date'),$charge,$unit,'$memo','$place')";



	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, $query);



mysqli_close($con);
?>