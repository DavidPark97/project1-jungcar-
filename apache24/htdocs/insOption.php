<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

$option_idx = $_POST['option_idx'];
$sell_idx = $_POST['sell_idx'];
    
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, "insert into sell_option (option_idx, sell_idx) values ($option_idx,$sell_idx)");


mysqli_close($con);
?>