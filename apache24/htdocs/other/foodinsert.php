<?php
$con=mysqli_connect("localhost","root","1234","everyhealth");


$user_idx = $_POST['user_idx'];
$food_idx = $_POST['food_idx'];
$date = $_POST['date'];
$gram = $_POST['gram'];

	

if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, "insert into daily_eat (user_idx,food_idx,date,gram) values ($user_idx,$food_idx,'$date',$gram)");

mysqli_close($con);
?>