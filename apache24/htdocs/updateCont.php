<?php
$con=mysqli_connect("localhost","root","1234","jungcar");


$sell_idx = $_POST['sell_idx'];

$price = $_POST['price'];

$detail = $_POST['detail'];

if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, "update car_market set price = $price, detail='$detail' where sell_idx = $sell_idx");


mysqli_close($con);
?>