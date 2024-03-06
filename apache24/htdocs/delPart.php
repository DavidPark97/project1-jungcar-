<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$market_idx = $_POST['market_idx'];

$result = mysqli_query($con, "select board_idx from part_market where market_idx=$market_idx");
$board = mysqli_fetch_array($result);
$board_idx = $board['board_idx'];



mysqli_query($con,"delete from  shboard where board_idx = $board_idx");




$result2=mysqli_query($con, "select comment_idx from comment where board_idx = $board_idx");
$rowCnt= mysqli_num_rows($result2);


for($i=0;$i<$rowCnt;$i++){
      $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
      $comment_idx = $row['comment_idx'];
      mysqli_query($con,"delete from recomment where comment_idx = $comment_idx");
      mysqli_query($con,"delete from cmt_reco where comment_idx = $comment_idx");
}
mysqli_query($con,"delete from comment where board_idx = $board_idx");


$result3=mysqli_query($con, "select img from part_img where market_idx = $market_idx");
$rowCnt2= mysqli_num_rows($result3);


for($i=0;$i<$rowCnt2;$i++){
      $row= mysqli_fetch_array($result3, MYSQLI_ASSOC);
      $img = $row['img'];
      unlink("$img");
}
mysqli_query($con,"delete from part_img where market_idx = $market_idx");

$result4=mysqli_query($con, "select img from part_market where market_idx = $market_idx");
$row= mysqli_fetch_array($result4, MYSQLI_ASSOC);
$img = $row['img'];
unlink("$img");

mysqli_query($con,"delete from board where board_idx = $board_idx");
mysqli_query($con,"delete from part_trade where market_idx = $market_idx");
mysqli_query($con,"delete from part_market where market_idx = $market_idx");

mysqli_close($con);
?>