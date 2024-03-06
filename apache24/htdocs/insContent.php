<?php
$con=mysqli_connect("localhost","root","1234","jungcar");


$user_idx = $_POST['user_idx'];
$board_type=$_POST['board_type'];
$subject = $_POST['subject'];
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

$result3 = mysqli_query($con, "insert into board (board_idx,board_type,user_idx,date,content,subject) values($board_idx,(select board_type from board_type where name ='$board_type'),$user_idx,'$now','$content','$subject')");

$arr=array("board_idx" => $board_idx);
$arr2=array($arr);
$jsonData=json_encode(array("webnautes"=>$arr2), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

echo $jsonData;
mysqli_close($con);
?>