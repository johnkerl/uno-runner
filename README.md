uno-runner
==========

This is a little Java simulator for the card game Uno, for investigating
fat-tailedness of game-length and other distributions.  It will become grist
for an upcoming blog post at http://exegetotrope.blogspot.com.  It's also an
example of my use of Java:  I use Java quite a bit on the job but (until now)
didn't have any examples of that on Github.

# Usage
* make all
* make tests
* java org/johnkerl/unorunner/UnoGameRunner 4
* java org/johnkerl/unorunner/UnoGameRunner 4 100
* make clean

# To examine
* Distribution of hand sizes overall; then, conditioned on game length (or,
  more coarsely, number of reshuffles).
* Expected next-turn hand size conditioned on current hand size.  Try to
  explain long-term game-length variability in terms of short-term hand-size
  pressures.
* Distribution of game lengths (in number of turns, and/or number of
  reshuffles).
* Distribution of draw sizes.

# Data being printed

Game-details mode ( `c2h` and other CSV tools are at https://github.com/johnkerl/scripts/tree/master/ntbl ):
```
$  java org/johnkerl/unorunner/UnoGameRunner 4 | c2h
nturn nshuf c0 c1 c2 c3 ndraw ndeck ndis card          
----- ----- -- -- -- -- ----- ----- ---- --------------
0     1     7  7  7  7  0     79    1    _             
1     1     6  _  _  _  0     79    2    wild/draw-four
2     1     _  11 _  _  4     75    2    _             
3     1     _  10 _  _  0     75    3    red/3         
4     1     _  _  6  _  0     75    4    red/9         
5     1     _  _  _  6  0     75    5    red/5         
6     1     5  _  _  _  0     75    6    red/6         
7     1     _  9  _  _  0     75    7    green/6       
8     1     _  _  5  _  0     75    8    blue/6        
9     1     _  _  _  5  0     75    9    yellow/6      
10    1     4  _  _  _  0     75    10   blue/6        
11    1     _  8  _  _  0     75    11   blue/7        
12    1     _  _  5  _  1     74    12   blue/2        
13    1     _  _  _  4  0     74    13   blue/8        
14    1     3  _  _  _  0     74    14   blue/2        
15    1     _  7  _  _  0     74    15   green/2       
16    1     _  _  4  _  0     74    16   green/9       
17    1     _  _  _  3  0     74    17   yellow/9      
18    1     2  _  _  _  0     74    18   yellow/3      
19    1     _  6  _  _  0     74    19   yellow/2      
20    1     _  _  3  _  0     74    20   yellow/0      
21    1     _  _  _  2  0     74    21   yellow/skip   
22    1     _  5  _  _  0     74    22   green/skip    
23    1     _  _  _  1  0     74    23   green/skip    
24    1     _  4  _  _  0     74    24   green/6       
25    1     _  _  2  _  0     74    25   red/6         
26    1     _  _  _  0  0     74    26   red/4         
```

Game-summary mode:
```
$ java org/johnkerl/unorunner/UnoGameRunner 4 10 | c2h
nturn nshuf
----- -----
29    1    
61    1    
52    1    
34    1    
179   2    
75    1    
588   8    
31    1    
73    1    
81    1    
```
