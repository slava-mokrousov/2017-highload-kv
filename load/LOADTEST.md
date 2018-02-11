# 3 этап - тестирование с Нагрузкой
Нагрузочное тестирование было проведено с помощью wrk в режимах 1/2/4 потока/соединения. Длительность 1 минута.
## До оптимизации

### PUT без перезаписи
##### 1 поток, 1 соединение
```
wrk --latency -t1 -c1 -d1m -s put.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     8.08ms   15.34ms 242.33ms   96.21%
    Req/Sec    89.41     19.04   111.00     88.47%
  Latency Distribution
     50%    5.17ms
     75%    5.54ms
     90%    6.82ms
     99%   89.40ms
  5321 requests in 1.00m, 488.45KB read
Requests/sec:     88.60
Transfer/sec:      8.13KB
```
##### 2 потока, 2 соединения
```
wrk --latency -t2 -c2 -d1m -s put.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     7.99ms   27.63ms 463.25ms   98.29%
    Req/Sec    89.82     10.14   108.00     87.78%
  Latency Distribution
     50%    4.80ms
     75%    5.08ms
     90%    5.52ms
     99%  115.71ms
  10666 requests in 1.00m, 0.96MB read
Requests/sec:    177.56
Transfer/sec:     16.30KB
```
##### 4 потока, 4 соединения
```
wrk --latency -t4 -c4 -d1m -s put.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    15.89ms   51.54ms 501.96ms   95.66%
    Req/Sec    72.23     11.79    90.00     93.37%
  Latency Distribution
     50%    5.85ms
     75%    6.83ms
     90%    9.24ms
     99%  330.51ms
  16758 requests in 1.00m, 1.50MB read
Requests/sec:    278.98
Transfer/sec:     25.61KB
```
### GET без повторов
##### 1 поток, 1 соединение
```
wrk --latency -t1 -c1 -d1m -s get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     6.10ms    8.66ms 110.03ms   90.80%
    Req/Sec   235.61    615.62     4.66k    97.83%
  Latency Distribution
     50%    4.87ms
     75%    6.09ms
     90%   14.05ms
     99%   41.77ms
  14065 requests in 1.00m, 57.38MB read
Requests/sec:    234.38
Transfer/sec:      0.96MB
```
##### 2 потока, 2 соединения
```
wrk --latency -t2 -c2 -d1m -s get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     6.42ms    9.04ms 115.27ms   90.86%
    Req/Sec   230.25    441.38     2.56k    95.99%
  Latency Distribution
     50%    5.05ms
     75%    6.15ms
     90%   14.56ms
     99%   47.27ms
  27477 requests in 1.00m, 112.10MB read
Requests/sec:    457.52
Transfer/sec:      1.87MB
```
##### 4 потока, 4 соединения
```
wrk --latency -t4 -c4 -d1m -s get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     6.27ms    8.46ms 118.48ms   90.79%
    Req/Sec   227.28    321.17     1.50k    92.64%
  Latency Distribution
     50%    5.37ms
     75%    6.76ms
     90%   13.94ms
     99%   41.79ms
  54239 requests in 1.00m, 221.29MB read
Requests/sec:    903.19
Transfer/sec:      3.68MB
```
### PUT c перезаписью
##### 1 поток, 1 соединение
```
wrk --latency -t1 -c1 -d1m -s put_rewrite.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     5.21ms    3.29ms  68.60ms   97.51%
    Req/Sec    99.29     10.27   111.00     84.87%
  Latency Distribution
     50%    4.87ms
     75%    5.07ms
     90%    5.30ms
     99%   13.15ms
  5939 requests in 1.00m, 545.18KB read
Requests/sec:     98.88
Transfer/sec:      9.08KB
```
##### 2 потока, 2 соединения
```
wrk --latency -t2 -c2 -d1m -s put_rewrite.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     5.51ms    7.97ms 203.47ms   98.61%
    Req/Sec    92.34      9.69   110.00     93.99%
  Latency Distribution
     50%    4.75ms
     75%    4.97ms
     90%    5.29ms
     99%   24.19ms
  11045 requests in 1.00m, 0.99MB read
Requests/sec:    183.88
Transfer/sec:     16.88KB
```
##### 4 потока, 4 соединения
```
wrk --latency -t4 -c4 -d1m -s put_rewrite.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     9.56ms   28.78ms 472.64ms   97.47%
    Req/Sec    71.28     11.99    90.00     88.82%
  Latency Distribution
     50%    5.48ms
     75%    6.74ms
     90%    9.14ms
     99%  151.22ms
  16905 requests in 1.00m, 1.52MB read
Requests/sec:    281.35
Transfer/sec:     25.83KB
```
### GET с повторами
##### 1 поток, 1 соединение
```
wrk --latency -t1 -c1 -d1m -s get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   607.02us    3.54ms 105.22ms   98.13%
    Req/Sec     4.39k     1.17k    4.85k    92.83%
  Latency Distribution
     50%  191.00us
     75%  195.00us
     90%  240.00us
     99%    7.64ms
  262155 requests in 1.00m, 1.04GB read
Requests/sec:   4368.94
Transfer/sec:     17.82MB
```
##### 2 потока, 2 соединения
```
wrk --latency -t2 -c2 -d1m -s get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   397.00us    0.98ms  51.22ms   99.82%
    Req/Sec     2.63k   133.10     2.78k    98.17%
  Latency Distribution
     50%  357.00us
     75%  364.00us
     90%  383.00us
     99%  597.00us
  314993 requests in 1.00m, 1.25GB read
Requests/sec:   5241.54
Transfer/sec:     21.38MB
```
##### 4 потока, 4 соединения
```
wrk --latency -t4 -c4 -d1m -s get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   687.56us  212.71us   5.17ms   75.99%
    Req/Sec     1.42k    57.97     2.50k    76.35%
  Latency Distribution
     50%  676.00us
     75%  836.00us
     90%    0.99ms
     99%    1.16ms
  340587 requests in 1.00m, 1.36GB read
Requests/sec:   5667.13
Transfer/sec:     23.12MB
```
### Смесь PUT/GET 50/50 без перезаписи
##### 1 поток, 1 соединение
```
wrk --latency -t1 -c1 -d1m -s put_get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     3.54ms    7.85ms 155.48ms   98.37%
    Req/Sec   184.69     21.83   217.00     92.07%
  Latency Distribution
     50%    4.50ms
     75%    5.04ms
     90%    5.36ms
     99%   25.77ms
  11039 requests in 1.00m, 23.02MB read
Requests/sec:    183.82
Transfer/sec:    392.45KB
```
##### 2 потока, 2 соединения
```
wrk --latency -t2 -c2 -d1m -s put_get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     3.56ms    3.18ms  82.87ms   94.62%
    Req/Sec   154.58     17.76   181.00     83.64%
  Latency Distribution
     50%    4.34ms
     75%    5.70ms
     90%    6.31ms
     99%   10.80ms
  18507 requests in 1.00m, 38.58MB read
Requests/sec:    308.16
Transfer/sec:    657.88KB
```
##### 4 потока, 4 соединения
```
wrk --latency -t4 -c4 -d1m -s put_get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     7.11ms   24.94ms 412.87ms   98.47%
    Req/Sec   117.29     10.96   141.00     93.75%
  Latency Distribution
     50%    4.73ms
     75%    6.42ms
     90%    7.92ms
     99%  112.77ms
  27854 requests in 1.00m, 58.07MB read
Requests/sec:    463.68
Transfer/sec:      0.97MB
```
### Смесь PUT/GET 50/50 с перезаписью
##### 1 поток, 1 соединение
```
wrk --latency -t1 -c1 -d1m -s put_get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     2.87ms    4.02ms  82.12ms   98.93%
    Req/Sec   189.06     17.35   215.00     95.45%
  Latency Distribution
     50%    1.66ms
     75%    4.85ms
     90%    5.07ms
     99%    7.46ms
  11301 requests in 1.00m, 23.56MB read
Requests/sec:    188.34
Transfer/sec:    402.09KB
```
##### 2 потока, 2 соединения
```
wrk --latency -t2 -c2 -d1m -s put_get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     6.81ms   25.21ms 337.51ms   97.57%
    Req/Sec   156.10     22.92   181.00     93.09%
  Latency Distribution
     50%    4.43ms
     75%    5.70ms
     90%    6.26ms
     99%  150.80ms
  18481 requests in 1.00m, 38.53MB read
Requests/sec:    307.52
Transfer/sec:    656.52KB
```
##### 4 потока, 4 соединения
```
wrk --latency -t4 -c4 -d1m -s put_get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    14.75ms   51.48ms 473.68ms   95.70%
    Req/Sec   113.86     18.29   155.00     93.93%
  Latency Distribution
     50%    4.86ms
     75%    6.86ms
     90%    8.39ms
     99%  331.32ms
  26309 requests in 1.00m, 54.85MB read
Requests/sec:    437.87
Transfer/sec:      0.91MB
```
## После оптимизации

Оптимизация была выполнена путем добавления кэша в хранилище

### PUT без перезаписи
##### 1 поток, 1 соединение
```
wrk --latency -t1 -c1 -d1m -s put.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     6.73ms   11.55ms 187.76ms   97.21%
    Req/Sec    94.25     17.20   111.00     91.71%
  Latency Distribution
     50%    4.85ms
     75%    5.21ms
     90%    6.06ms
     99%   68.09ms
  5616 requests in 1.00m, 515.53KB read
Requests/sec:     93.58
Transfer/sec:      8.59KB
```
##### 2 потока, 2 соединения
```
wrk --latency -t2 -c2 -d1m -s put.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     7.59ms   19.88ms 276.92ms   97.73%
    Req/Sec    89.99     13.35   111.00     89.82%
  Latency Distribution
     50%    4.62ms
     75%    5.09ms
     90%    6.36ms
     99%  122.09ms
  10676 requests in 1.00m, 0.96MB read
Requests/sec:    177.84
Transfer/sec:     16.32KB
```
##### 4 потока, 4 соединения
```
wrk --latency -t4 -c4 -d1m -s put.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    11.03ms   32.84ms 398.85ms   96.93%
    Req/Sec    71.06     11.87    90.00     86.42%
  Latency Distribution
     50%    5.62ms
     75%    7.20ms
     90%    9.77ms
     99%  213.33ms
  16754 requests in 1.00m, 1.50MB read
Requests/sec:    278.82
Transfer/sec:     25.60KB
```
### GET без повторов
##### 1 поток, 1 соединение
```
wrk --latency -t1 -c1 -d1m -s get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     3.33ms    6.46ms 132.31ms   88.75%
    Req/Sec     2.15k     3.88k   10.14k    78.30%
  Latency Distribution
     50%   99.00us
     75%    4.00ms
     90%   10.98ms
     99%   26.80ms
  128423 requests in 1.00m, 523.94MB read
Requests/sec:   2137.71
Transfer/sec:      8.72MB
```
##### 2 потока, 2 соединения
```
wrk --latency -t2 -c2 -d1m -s get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     2.18ms    6.74ms 150.21ms   95.50%
    Req/Sec     2.28k     2.87k    6.54k    65.43%
  Latency Distribution
     50%  150.00us
     75%    2.35ms
     90%    4.69ms
     99%   27.35ms
  245676 requests in 1.00m, 0.98GB read
  Socket errors: connect 0, read 0, write 0, timeout 2
Requests/sec:   4094.20
Transfer/sec:     16.70MB
```
##### 4 потока, 4 соединения
```
wrk --latency -t4 -c4 -d1m -s get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.43ms    3.53ms 105.59ms   93.47%
    Req/Sec     2.22k     1.60k    3.83k    62.38%
  Latency Distribution
     50%  308.00us
     75%    0.94ms
     90%    4.21ms
     99%   14.02ms
  529364 requests in 1.00m, 2.11GB read
Requests/sec:   8812.49
Transfer/sec:     35.95MB
```
### PUT c перезаписью
##### 1 поток, 1 соединение
```
wrk --latency -t1 -c1 -d1m -s put_rewrite.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    10.34ms   31.04ms 370.88ms   96.46%
    Req/Sec    96.74     16.92   116.00     92.92%
  Latency Distribution
     50%    4.74ms
     75%    5.05ms
     90%    6.00ms
     99%  195.79ms
  5682 requests in 1.00m, 521.59KB read
Requests/sec:     94.58
Transfer/sec:      8.68KB
```
##### 2 потока, 2 соединения
```
wrk --latency -t2 -c2 -d1m -s put_rewrite.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     8.25ms   26.88ms 395.35ms   98.17%
    Req/Sec    90.17     12.94   111.00     87.18%
  Latency Distribution
     50%    4.66ms
     75%    5.29ms
     90%    6.73ms
     99%  149.75ms
  10675 requests in 1.00m, 0.96MB read
Requests/sec:    177.78
Transfer/sec:     16.32KB
```
##### 4 потока, 4 соединения
```
wrk --latency -t4 -c4 -d1m -s put_rewrite.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    12.14ms   40.74ms 538.19ms   96.93%
    Req/Sec    71.13     11.68    90.00     84.71%
  Latency Distribution
     50%    5.56ms
     75%    7.22ms
     90%    9.82ms
     99%  253.53ms
  16724 requests in 1.00m, 1.50MB read
Requests/sec:    278.26
Transfer/sec:     25.54KB
```
### GET с повторами
##### 1 поток, 1 соединение
```
wrk --latency -t1 -c1 -d1m -s get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    94.46us   53.76us   3.00ms   97.43%
    Req/Sec     9.12k     0.91k   10.10k    64.56%
  Latency Distribution
     50%   83.00us
     75%   97.00us
     90%  103.00us
     99%  222.00us
  545407 requests in 1.00m, 2.17GB read
Requests/sec:   9075.19
Transfer/sec:     37.03MB
```
##### 2 потока, 2 соединения
```
wrk --latency -t2 -c2 -d1m -s get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   160.78us   68.42us   3.35ms   95.20%
    Req/Sec     5.67k   521.80     6.44k    48.75%
  Latency Distribution
     50%  149.00us
     75%  171.00us
     90%  191.00us
     99%  400.00us
  678205 requests in 1.00m, 2.70GB read
Requests/sec:  11284.55
Transfer/sec:     46.04MB
```
##### 4 потока, 4 соединения
```
wrk --latency -t4 -c4 -d1m -s get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   285.66us  133.82us   8.97ms   83.47%
    Req/Sec     3.32k   290.61     5.88k    60.16%
  Latency Distribution
     50%  272.00us
     75%  339.00us
     90%  404.00us
     99%  662.00us
  792839 requests in 1.00m, 3.16GB read
Requests/sec:  13192.37
Transfer/sec:     53.82MB
```
### Смесь PUT/GET 50/50 без перезаписи
##### 1 поток, 1 соединение
```
wrk --latency -t1 -c1 -d1m -s put_get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     6.25ms   29.96ms 491.92ms   98.13%
    Req/Sec   187.97     22.07   213.00     87.18%
  Latency Distribution
     50%    4.37ms
     75%    4.74ms
     90%    5.31ms
     99%  156.74ms
  11089 requests in 1.00m, 23.12MB read
Requests/sec:    184.59
Transfer/sec:    394.09KB
```
##### 2 потока, 2 соединения
```
wrk --latency -t2 -c2 -d1m -s put_get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     7.73ms   27.09ms 353.00ms   97.05%
    Req/Sec   151.29     26.37   191.00     80.71%
  Latency Distribution
     50%    4.40ms
     75%    5.87ms
     90%    6.95ms
     99%  173.86ms
  17807 requests in 1.00m, 37.12MB read
Requests/sec:    296.47
Transfer/sec:    632.92KB
```
##### 4 потока, 4 соединения
```
wrk --latency -t4 -c4 -d1m -s put_get.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    12.37ms   39.12ms 388.06ms   95.86%
    Req/Sec   109.87     19.85   141.00     87.55%
  Latency Distribution
     50%    4.89ms
     75%    7.25ms
     90%    9.10ms
     99%  247.31ms
  25627 requests in 1.00m, 53.43MB read
Requests/sec:    426.43
Transfer/sec:      0.89MB
```
### Смесь PUT/GET 50/50 с перезаписью
##### 1 поток, 1 соединение
```
wrk --latency -t1 -c1 -d1m -s put_get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  1 threads and 1 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     3.30ms    7.79ms 180.55ms   98.81%
    Req/Sec   184.67     22.09   215.00     84.49%
  Latency Distribution
     50%    1.67ms
     75%    4.77ms
     90%    5.56ms
     99%   16.22ms
  11042 requests in 1.00m, 23.02MB read
Requests/sec:    183.77
Transfer/sec:    392.31KB
```
##### 2 потока, 2 соединения
```
wrk --latency -t2 -c2 -d1m -s put_get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  2 threads and 2 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     5.09ms   15.62ms 284.39ms   98.44%
    Req/Sec   148.06     21.51   181.00     72.77%
  Latency Distribution
     50%    4.35ms
     75%    5.83ms
     90%    6.78ms
     99%   53.91ms
  17625 requests in 1.00m, 36.75MB read
Requests/sec:    293.40
Transfer/sec:    626.37KB
```
##### 4 потока, 4 соединения
```
wrk --latency -t4 -c4 -d1m -s put_get_repeat.lua http://localhost:8080
Running 1m test @ http://localhost:8080
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    14.31ms   45.46ms 407.61ms   95.42%
    Req/Sec   107.23     20.32   141.00     88.54%
  Latency Distribution
     50%    5.19ms
     75%    7.51ms
     90%    9.93ms
     99%  289.93ms
  24759 requests in 1.00m, 51.61MB read
Requests/sec:    412.57
Transfer/sec:      0.86MB
```

За счет введения кэша в DAO мы получили большой прирост в производительности GET с повторами.
Так же был получен прирост в производительности GET без повторов, хотя этот прирост непонятен, возможно проблема в жестком диске.