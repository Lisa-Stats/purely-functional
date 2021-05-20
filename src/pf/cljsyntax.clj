(ns pf.cljsyntax)

(comment
  ;;clojure syntax
  ;;for comprehensions
  ;;it is for list comprehensions
  ;;it is lazy
  (def nums (for [x (range 10)]
              (println (inc x))))

  (first nums)

  ;;very useful
  ;;looks clean
  (map inc (filter even? (filter pos? (range 10)))) ;;not clean

  (for [x (range 10) ;;more clean
        :when (pos? x)
        :when (even? x)]
    (inc x))

  (def x-axis [0 1 2 3 4 5])
  (def y-axis [0 1 2 3 4 5 6 7])

  (for [x x-axis
        y y-axis
        :when (= x y)]
    [x y]))
