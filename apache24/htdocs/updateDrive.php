<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$drive_idx = $_POST['drive_idx'];
$date = $_POST['date'];
$distance = $_POST['distance'];
$memo = $_POST['memo'];





header('content-type: text/html; charset=utf-8');



$query = "update drive_history set date='$date',distance=$distance,memo='$memo' where drive_idx=$drive_idx";



	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, $query);



mysqli_close($con);
?>