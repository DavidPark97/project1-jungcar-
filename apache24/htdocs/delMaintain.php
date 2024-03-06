<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

$main_idx = $_POST['main_idx'];

	


if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();


$result=mysqli_query($con, "delete from maintain_history where main_idx = $main_idx");


mysqli_close($con);
?>