<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$main_idx = $_POST['main_idx'];
$date = $_POST['date'];
$center_idx = $_POST['center_idx'];
$charge = $_POST['charge'];





header('content-type: text/html; charset=utf-8');



$query = "update maintain_history set date='$date',center_idx=$center_idx,charge=$charge where main_idx=$main_idx";



	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, $query);



mysqli_close($con);
?>