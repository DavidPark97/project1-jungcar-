<?php
$con=mysqli_connect("localhost","root","1234","everyhealth");

$user = $_POST['user_idx'];
$routine_idx = $_POST['routine'];
$work_idx = $_POST['work'];

$query1 = "DELETE FROM routine WHERE user_idx = ".$user." and routine_idx = $routine_idx and workout_idx = ".$work_idx.";";
$result = mysqli_query($con, $query1);

mysqli_close($con);
?>