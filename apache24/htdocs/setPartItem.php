<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$market_idx = $_POST['market_idx'];




header('content-type: text/html; charset=utf-8');



$query =  "select board.user_idx,subject,part_trade_idx,img,content,board.date,price,delivery,state,board.board_idx,
(select count(shboard.board_idx) from shboard where board.board_idx = shboard.board_idx group by shboard.board_idx) as number, type_name
from part_market left outer join part_trade on part_market.market_idx =part_trade.market_idx ,part_type, board left outer join shboard on board.board_idx = shboard.board_idx
where  part_market.board_idx = board.board_idx and part_type.part_type = part_market.part_type
 and part_market.market_idx=$market_idx group by board.board_idx ";



	
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