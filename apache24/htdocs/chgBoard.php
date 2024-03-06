<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$board_idx =  $_POST['board_idx'];
$board_type = $_POST['board_type'];




header('content-type: text/html; charset=utf-8');



$query ="select A.next, A.pre from (select board_idx, LEAD(board_idx, 1) over (order by date asc) as next, LAG(board_idx,1) over (order by date asc) as pre 
from board where board_type = (select board_type from board_type where name ='$board_type') ) A where A.board_idx = $board_idx;";
	
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