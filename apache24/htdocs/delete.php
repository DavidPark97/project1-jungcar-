<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

$user = $_POST['user'];
$sell_idx = $_POST['sell_idx'];
$table = $_POST['table'];


	


if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();


$result=mysqli_query($con, "delete from $table where user_idx = $user and sell_idx = $sell_idx");


mysqli_close($con);
?>