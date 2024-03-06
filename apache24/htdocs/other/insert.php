<?php
$con=mysqli_connect("localhost","root","1234","everyhealth");


$user_idx = $_POST['user_idx'];
$workout_idx = $_POST['workout_idx'];
$date = $_POST['date'];
$weight = $_POST['weight'];
$count = $_POST['count'];

	

if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, "insert into daily_data (user_idx,workout_idx,date,weight,count) values ($user_idx,$workout_idx,'$date',$weight,$count)");

mysqli_close($con);
?>