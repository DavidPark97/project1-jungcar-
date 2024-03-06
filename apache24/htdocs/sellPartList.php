<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$user = $_POST['user'];






header('content-type: text/html; charset=utf-8');



$query = " select subject, price, date,img,market_idx,(select count(board_idx) from comment where board.board_idx = comment.board_idx group by (board_idx)) as cnt
 from board,part_market where board.board_idx = part_market.board_idx and user_idx=$user";


	
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