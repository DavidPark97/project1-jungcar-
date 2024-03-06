<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

$seller = $_POST['seller'];
$table = $_POST['table'];
$sell_idx = $_POST['sell_idx'];
$buyer = $_POST['buyer']; 
$now = date("Y-m-d H:i:s");
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

if($table==="trade"){
$result=mysqli_query($con, "insert into trade (sell_idx,buyer,seller,date) values ($sell_idx,(select user_idx from user where id='$buyer'),$seller,'$now')");
}else if($table==="part_trade"){
    $result=mysqli_query($con, "insert into part_trade (market_idx,buyer,seller,date) values ($sell_idx,(select user_idx from user where id='$buyer'),$seller,'$now')");

}

mysqli_close($con);
?>