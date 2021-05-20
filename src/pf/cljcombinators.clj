(ns pf.cljcombinators)

;;identity
;;effectively is 0 bc returns answers, keeps other answers the same
;;eg. zero - 1 + 0 = 1
(filter identity [1 2 nil 9])


;;constantly
(def always5 (constantly 5))

;;constantly makes a new fn that always returns the arg to constantly
(always5 5)
(always5 100)
(always5 nil)
(always5 6 :hello nil [])
;;need a fn that returns a known value but cannot just give it the value,
;;need to lift that value up into a fn context


;;partial
;;good to use when know one of the args
(def add3 (partial + 3))
(add3 5)

(def one-over (partial / 1))
(one-over 2)


;;fnil
;;when get nil, give it a default
(def inc-default0 (fnil inc 0))
(inc-default0 nil)


;;comp
;;stands for functional composition
(def person {:name "Luke S"
             :address {:street "22 Sky Way"
                       :planet "Tatouine"
                       :postal-code "88596a"}})

(defn postalcode [person]
  (:postal-code  (:address person)))

(postalcode person)

((comp :postal-code :address) person)

(def nodes [{:name :a :children [:b :c]} {:name :b} {:name :c :children []}])

(filter (comp empty? :children) nodes)


;;juxt
;;extract values from map
((juxt :a :b) {:a 1 :b 2 :c 3 :d 4})

;;when we want to get representation of a value in a map, 'explodes' a value
(into {} (map (juxt identity str) (range 10)))
(into {} (map (juxt identity name) [:a :b :c :d]))

;;sort list of maps by multiple values
(sort-by (juxt :a :b) [{:a 1 :b 3} {:a 1 :b 2} {:a 2 :b 1}])

;;segregate even and odd numbers in collection
((juxt (partial filter even?) (partial filter odd?)) (range 0 9))


;;apply
;;allows you to apply function to a list
;;if do not know what a fn does, break it down into parts
;;send small part to repl to see what it does
((fn [values]
   (map #(vector (count %) (first %))
        (partition-by identity values)))
 [:a :a :b :b :b :c])

(partition-by identity [:a :b :b :c :c :c])
