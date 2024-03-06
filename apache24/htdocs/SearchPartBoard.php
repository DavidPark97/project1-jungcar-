<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

$part_type = $_POST['part_type'];
$keyword = $_POST['query'];






header('content-type: text/html; charset=utf-8');



$query = "select part_market.market_idx, subject, id, date(board.date) as date, img, 
(select count(board_idx) from comment where board.board_idx = comment.board_idx group by (board_idx)) as cnt, 
(select count(board_idx) from shboard where shboard.board_idx = board.board_idx group by (board_idx)) as number, 
part_trade_idx from part_type,board, user, part_market left outer join part_trade on part_trade.market_idx = part_market.market_idx 
where subject like '%$keyword%' and
 board.board_idx = part_market.board_idx and user.user_idx = board.user_idx and part_type.part_type = part_market.part_type ";

if(strcmp($part_type,"0")!=0){
  $query = $query. "and part_type.part_type=$part_type";
}

$query = $query. " order by market_idx desc";
	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, $query);
$rowCnt= mysqli_num_rows($result);
$arr= array();

for($i=0;$i<$rowCnt;$i++){
      $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
        $arr[$i]= $row;      
}

  $jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo "$jsonData";

mysqli_close($con);
?>