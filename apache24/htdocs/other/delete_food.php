<?php
$con=mysqli_connect("localhost","root","1234","everyhealth");

$user = $_POST['user_idx'];
$date = $_POST['date'];
$food_idx = $_POST['food'];

$query1 = "DELETE FROM daily_eat WHERE user_idx = ".$user." and date = '$date' and food_idx = ".$food_idx.";";
$result = mysqli_query($con, $query1);

mysqli_close($con);
?>