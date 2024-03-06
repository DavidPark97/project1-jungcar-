<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

$board_idx = $_POST['board_idx'];
$user_idx = $_POST['user_idx'];





header('content-type: text/html; charset=utf-8');



$query = "select id as user, date(board.date) as date, subject,content,
ifnull((select count(board_idx) from comment where board.board_idx = comment.board_idx group by (board_idx)),0) as cmtcnt,
 ifnull((select count(board_idx) from shboard where shboard.board_idx = board.board_idx group by (board_idx)),0) as shnum, 
  ifnull((select count(board_idx) from reco where reco.board_idx = board.board_idx group by (board_idx)),0) as reco,
  ifnull((select count(*) from reco where board_idx = $board_idx and user_idx = $user_idx),0) as state
from board,user 
where user.user_idx = board.user_idx and board_idx = $board_idx;";


	
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