<?php
$con=mysqli_connect("localhost","root","1234","everyhealth");

$user = $_POST['user_idx'];
$date = $_POST['date'];
$work_idx = $_POST['work'];

$query1 = "DELETE FROM daily_data WHERE user_idx = ".$user." and date = '$date' and workout_idx = ".$work_idx.";";
$result = mysqli_query($con, $query1);

mysqli_close($con);
?>