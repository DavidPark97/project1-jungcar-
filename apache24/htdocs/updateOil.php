<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$oil_idx = $_POST['oil_idx'];
$date = $_POST['date'];
$place = $_POST['place'];
$charge = $_POST['charge'];
$unit = $_POST['unit'];
$memo = $_POST['memo'];





header('content-type: text/html; charset=utf-8');



$query = "update oil_history set date='$date',place='$place',memo='$memo',unit=$unit,charge=$charge where oil_idx = $oil_idx";



	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, $query);



mysqli_close($con);
?>