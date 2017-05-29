set terminal postscript eps size 5in, 3.2in # 出力先をEPSに設定
#set terminal postscript enhanced color size 5in, 3in

####### 出力ファイル名 ########
set output '/Users/Hideo/Documents/篠宮研究室/simulator/LeaderElection/gnuplot/figure/NWS_BFS_probability_characteristics.eps'

#set style boxplot outliers pointtype 10
#set style data boxplot


####### 入力ファイル名 ########
n=0
set datafile separator ","
plot "csv/NWS_BFS_SharedVertex_prob_characteristics.csv" using 1:2 w lp lc 2 pt 4 ps 1.5 dt 1 title "# of edges","csv/NWS_BFS_SharedVertex_prob_characteristics.csv" using 1:3 w lp lc 2 pt 5 ps 1.5 dt 1 title "diameter","csv/NWS_BFS_SharedVertex_prob_characteristics.csv" using 1:4 w lp lc 2 pt 6 ps 1.5 dt 1 title "clustering coefficient","csv/NWS_BFS_SharedVertex_prob_characteristics.csv" using 1:5 w lp lc 2 pt 7 ps 1.5 dt 1 title "# of cycles","csv/NWS_BFS_SharedVertex_prob_characteristics.csv" using 1:6 w lp lc 2 pt 8 ps 1.5 dt 1 title "# of adjacent cycles","csv/NWS_BFS_SharedVertex_prob_characteristics.csv" using 1:7 w lp lc 2 pt 9 ps 1.5 dt 1 title "cycle size (av)","csv/NWS_BFS_SharedVertex_prob_characteristics.csv" using 1:8 w lp lc 2 pt 10 ps 1.5 dt 1 title "cycle size (std)", n notitle

#set size ratio 0.6

#pt 1 ps 0.5
#凡例の削除
#set key right top

#y軸格子線
#set grid linetype 1 lc rgb "dark-gray" ytics mytics

#凡例の枠線
set key right top font  'Helvetica,25'

#x, y表示幅の設定
set xrange[0.03:0.23]
set yrange[-0.25:1.5]
 
#set ytics(0.1, 0.3, 0.5, 0.7, 0.9, 1.1)

#font種類、サイズを指定してx, y軸ラベルを書き込む
set ylabel "Change ratio" font 'Helvetica,22'
set xlabel "Probablity" font 'Helvetica,22'

#x, y座標を指定してlabelを書き込む
#set label 1 'tree 0' at 0.513160789651816,0.364034069544402 center
#set label 2 'tree 46' at 0.6153251313485925,0.33365837057830067 center

#labelに対してフォント種類とサイズを指定
set label 1 font 'Helvetica,19' 
set label 2 font 'Helvetica,19'

#軸の数字のフォント種類とサイズを指定 
set tics font "Helvetica,22"

#グラフ出力時の余白調整
#set bmargin 1
#出力先を戻す
#set terminal x11
set output