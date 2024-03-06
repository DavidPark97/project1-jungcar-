<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$user = $_POST['user'];
$market_idx = $_POST['market_idx'];



header('content-type: text/html; charset=utf-8');



$query = "select shboard_idx from shboard, board, part_market 
where part_market.board_idx = board.board_idx and board.board_idx = shboard.board_idx and market_idx = $market_idx and shboard.user_idx = $user";

	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();
$rowCnt = 0;
$result=mysqli_query($con, $query);
$rowCnt= mysqli_num_rows($result);

if($rowCnt==0){
    $query2 = "insert into shboard (user_idx,board_idx) values ($user,(select board.board_idx from part_market,board where part_market.board_idx = board.board_idx and market_idx=$market_idx))";
    $result2=mysqli_query($con, $query2);
}


mysqli_close($con);
?>