(ns pf.recursion)

(def ls0 [])
(def ls1 [1])
(def ls10 [range 10])

(defn length [ls]
  (if (empty? ls)
    0
    (+ 1 (length (rest ls)))))

;;this is classic recursion - recursing thru data structure based
;;on its structure
;;recursion has 3 parts - base case: the end state, until when you are
;;recursing until
;;- has to advance at each recursion, advance thru data structure, do that
;;using rest
;;- initialization: initializing it by passing a ls to the length fn
(length nil)
(length ls0)
(length ls1)
(length ls10)


;;implementing map with recursion
(defn map* [f ls]
  (if (empty? ls)
    () ;;base case, get this proper and then go for it
    (cons(f (first ls)) ;;advancement
         (map* f (rest ls))))) ;;recursion

(map* inc [])
(map* inc (range 10))
(map* str (range 5))


;;implementing filter with recursion
(defn filter* [p? ls]
  (if (empty? ls)
    ()
    (if (p? (first ls))
      (cons (first ls) (filter* p? (rest ls)))
      (filter* p? (rest ls)))))


;;filter with tail recursion
(defn filters* [p? ls]
  (if (empty? ls)
    ()
    (if (p? (first ls))
      (cons (first ls) (filters* p? (rest ls))) ;;this filters* has somethingdone to it before it is returned,  needs a stackframe, where the idea of whatodo next is stored
      (filters* p? (rest ls))))) ;; this filters* value is returned directly,can change this to recur fn bc it is in tail position, called tail recursion


;;filter with tail recursion
(defn filter*-helper [p? ls acc]
  (if (empty? ls)
    acc
    (if (p? (first ls))
      (recur p? (rest ls) (conj acc (first ls)))
      (recur p? (rest ls) acc))))
;;add with rest
;;conj the number to acc that we want to keep
;;if not, just return the acc unchanged

(defn filterz* [p? ls]
  (filter*-helper p? ls []))

(filterz* even? (range 10))


;;map with tail recursion
(defn map*-helper [f ls acc]
  (if (empty? ls)
    acc ;;store all work as we recurse down the list
    (recur f (rest ls) (conj acc (f (first ls))))))
;;advance with rest
;;adding (f (first ls))

(defn maps* [f ls]
  (map*-helper f ls []))

(maps* inc (range 10))


;;length with tail recursion
(defn length*-helper [ls acc]
  (if (empty? ls)
    acc
    (recur (rest ls) (+ 1 acc))))

(defn lengths* [ls]
  (length*-helper ls 0))

(lengths* [0 1 2 3])


;;how is recursion like a for loop
(defn read-book [book]
  (if (empty? book)
    nil
    (do
      (read (first book))
      (recur (rest book)))))


;;recursion and laziness
;;makes sense to make map and filter lazy because they build up a new seq
;;they take a lazy seq and build a lazy seq, they are seq fns
;;using lazy-seq is just like with tail-recursive version, doesn't use stack
;;b/c nothing happens yet, just builds up the fn on how to generate the next
;;part, doesn't run it until you need it
(defn map-lazy [f ls]
  (lazy-seq
   (if (empty? ls)
     ()
     (cons (f (first ls))
          (map* f (rest ls))))))

(defn filter-lazy [p? ls]
  (lazy-seq
   (if (empty? ls)
     ()
     (if (p? (first ls))
       (cons (first ls) (filter* p? (rest ls)))
       (filter* p? (rest ls))))))


;;loop/recur
(defn map-loop [f ls]
  (loop [ls ls acc []] ;;initialization
    (if (empty? ls)
      acc ;;base case
      (recur (rest ls) (conj acc (f (first ls)))))))

(defn filter-loop [p? ls]
  (loop [ls ls acc []]
    (if (empty? ls)
      acc
      (if (p? (first ls))
        (recur (rest ls) (conj acc (first ls)))
        (recur (rest ls) acc)))))
