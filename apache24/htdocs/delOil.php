<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

$oil_idx = $_POST['oil_idx'];

	


if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();


$result=mysqli_query($con, "delete from oil_history where oil_idx = $oil_idx");


mysqli_close($con);
?>