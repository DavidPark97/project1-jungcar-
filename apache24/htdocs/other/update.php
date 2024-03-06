<?php
$con=mysqli_connect("localhost","root","1234","everyhealth");


$user_idx = $_POST['user_idx'];
$workout_idx = $_POST['workout_idx'];
$date = $_POST['date'];
$weight = $_POST['weight'];
$count = $_POST['count'];

	

if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, "update daily_data set weight=$weight, count=$count where date = '$date' and user_idx=$user_idx and workout_idx=$workout_idx");

mysqli_close($con);
?>