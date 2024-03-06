<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

$user = $_POST['user'];
$sell_idx = $_POST['sell_idx'];

	


if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();


$result=mysqli_query($con, "insert into bookmark (user_idx,sell_idx) values ($user,$sell_idx)");


mysqli_close($con);
?>