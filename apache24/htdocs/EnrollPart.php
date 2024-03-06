<?php
$con=mysqli_connect("localhost","root","1234","jungcar");


$user_idx = $_POST['user_idx'];
$part_type=$_POST['type_name'];
$price = $_POST['price'];
$subject = $_POST['subject'];
$state=$_POST['state'];
$delivery = $_POST['delivery'];
$agree=$_POST['agree'];
$content=$_POST['content'];
$now = date("Y-m-d H:i:s");

if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();


$result=mysqli_query($con, "select max(board_idx)+1 as board_idx from board");

$board = mysqli_fetch_array($result);
$board_idx = $board['board_idx'];
if($board_idx==NULL){
    $board_idx="1";
}

$result2=mysqli_query($con, "select max(market_idx)+1 as market_idx from part_market");
$market = mysqli_fetch_array($result2);
$market_idx = $market['market_idx'];
if($market_idx==NULL){
    $market_idx="1";
}

$result3 = mysqli_query($con, "insert into board (board_idx,board_type,user_idx,date,content,subject) values($board_idx,1,$user_idx,'$now','$content','$subject')");
$result4 = mysqli_query($con, "insert into part_market (market_idx,part_type,board_idx,price,agree,delivery,state) values($market_idx,$part_type,$board_idx,$price,'$agree','$delivery','$state')");

$arr=array("market_idx" => $market_idx);
$arr2=array($arr);
$jsonData=json_encode(array("webnautes"=>$arr2), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

echo $jsonData;
mysqli_close($con);
?>