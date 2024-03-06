<?php
$con=mysqli_connect("localhost","root","1234","everyhealth");


$user_idx = $_POST['user_idx'];
$food_idx = $_POST['food_idx'];
$date = $_POST['date'];
$gram = $_POST['gram'];


	

if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, "update daily_eat set gram=$gram where date = '$date' and user_idx=$user_idx and food_idx=$food_idx");

mysqli_close($con);
?>