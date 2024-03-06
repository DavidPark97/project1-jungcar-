<?php
$con=mysqli_connect("localhost","root","1234","everyhealth");


$user_idx = $_POST['user_idx'];
$workout_idx = $_POST['workout_idx'];
$routine_idx = $_POST['routine_idx'];
$weight = $_POST['weight'];
$count = $_POST['count'];

$query1 = "SELECT max(orders) as max FROM routine WHERE user_idx=$user_idx and routine_idx=$routine_idx;";
$result = mysqli_fetch_array(mysqli_query($con, $query1));

$order = $result["max"];	
$order = $order + 1;
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result2=mysqli_query($con, "insert into routine (user_idx,workout_idx,routine_idx,weight,targets,orders) values ($user_idx,$workout_idx,$routine_idx,$weight,$count,$order)");

mysqli_close($con);
?>