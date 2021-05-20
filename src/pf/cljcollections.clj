(ns pf.cljcollections)

(comment
  ;;clojure collections
  ;;access patterns
  ;;- collections form heart of the language
  ;;- were making a data structure that we will have
  ;;  to access later
  ;;  -> need to think about how we will be accessing it
  ;;  --> eg. add, modify, get stuff out
  ;;- data structures are organized around access patterns
  ;;- looking at domain
  ;;-> thinking how will I access this info?
  ;;-> eg. always access in certain order, access randomly,
  ;;want to count unique things, filter our specific things
  ;;-> will change what data structure will be used

  ;;sequential access pattern
  ;;- one of the most common ways to access data,
  ;;is to access all of the items in some order
  ;;- seq -> lets you get all of the items from coll,
  ;;  one at a time
  ;;  -> what order they come back in is the questions
  ;;  --> coll itself determines the order

  ;;arbitrary order - stable, order is maintained
  ;;have 3 options:
  ;;list
  (def l (list 1 2 3 4))
  (seq l)
  ;;vector
  ;;queue

  ;;sorted order
  ;;have 2 options:
  ;;sorted hashmap
  (def shm (sorted-map :a 1 :b 2))
  (seq shm)

  ;;sorted set
  (seq (sorted-set 1 2 3 4 5 0))

  (sorted-set-by (fn [a b]
                   (compare (:name a) (:name b)))
                 {:name "z"}
                 {:name "K"}
                 {:name "J"})

  ;;eg.
  ;;;depending on what type of data structure you give the
  ;;;atom, determines how the data behaves
  ;;;eg. added to the front, sorted or not
  (def todos (atom []))

  (defn add-todo! [item]
    (swap! todos conj item))

  (do
    (add-todo! "Buy kitten")
    (add-todo! "Buy cat food")
    (add-todo! "Feed kitten")

    (seq @todos)))


(comment
  ;;remembering duplicates access pattern
  ;;these remember duplicates:
  ;;list
  ;;vector
  ;;queue

  ;;forget duplicates:
  ;;set
  ;;hashmap - duplicates of key b/c that is the identity
  (conj {} [1 :a] [2 :a] [2 :b] [3 :c] [3 :d])

  ;;eg.
  ;;b/c atom is given a map, it does not duplicate like a
  ;;proper elevator
  ;;but if give it a vector, it will duplicate
  (def elevator-calls (atom {}))

  (defn press! [floor direction]
    (swap! elevator-calls
           conj [[floor direction] :called]))

  (press! 3 :up)
  (press! 3 :up))


(comment
  ;;lookup by key access pattern
  ;;where we have data indexed in a data structure
  ;;and take it out quickly using that index
  ;;two options:
  ;;hashmap (or sorted map)
  ;;keys and values can be any types
  (def rolodex {"Eric" "504-333-2212"
                "Jane" "202-223-3292"
                "Joe" "232-232-3434"})

  (get rolodex "Jane")

  (get rolodex "Fred" :unknown)

  ;;vector
  (def coords [10.2 4.5, 8.9])

  (def x (get coords 0))
  (def y (get coords 1))
  (def z (get coords 2))

  (str x ";" y ";" z)

  ;;set (kind of)
  (def friends #{"joe" "k" "l"})

  (get friends "p"))


(comment
  ;;assoc key and value access pattern
  ;;two options
  ;;hashmap
  (assoc {} :a 1)
  (assoc {:a 1} :b 2 :c 3 :a 9)

  ;;vector
  ;;type of key is fixed, it has to be a non-(-) integer
  ;;cannot have gaps in them
  (assoc [0 1 2 3 4] 1 :one)

  (update {:a 1} :a inc)

  (update {:a 1} :a + 4 2)

  (update-in {:a {:xs 2}} [:a :xs] + 3))


(comment
  ;;dissoc a key and value access pattern
  ;;hashmap
  (dissoc {:a 1 :b 2} :a)

  (def visits (atom {}))

  (defn record-visit! [ip]
    (swap! visits update ip (fnil inc 0)))

  (record-visit! "1.1.1")
  (record-visit! "1.1.2")
  (record-visit! "127.0.0.1")

  @visits

  (dissoc @visits "127.0.0.1"))


(comment
  ;;count the elements access pattern
  ;;all colls support it
  ;;constant time - fast
  (count {:a 1 :b 2})

  ;;lazy seqs
  ;;has to walk through every node - slow b/c linear
  ;;don't call count

  ;;eg.
  (def visits-two (atom {"1.1.1.1" 102
                         "2.2.2.2" 80
                         "127.0.0.1" 1008}))

  (count @visits-two) ;;gives # of unique keys
  )


(comment
  ;;equality comparisons access patterns
  ;;based on value, not on reference
  ;;equality partitions
  ;;sequential partition
  (= [1 2 3] '(1 2 3))
  (= [1 2 3] '(3 2 1)) ;;false

  ;;map equality partition
  (= {:a 2} {:a 2})
  (= {:a 1 :b 2} {:b 2 :a 1})

  ;;set equality partition
  (= #{1 2 3} #{3 2 1})

  ;;across partitions
  (= [1 2 3] #{1 2 3}) ;;false

  (=
   (seq {:a 1 :b 2})
   (seq {:b 2 :a 1})) ;;false b/c compared on order

  (hash [1 2 3])
  (hash '(1 2 3))

  (def my-map {[1 2 3] :answer})

  (get my-map [1 2 3])
  (get my-map '(1 2 3)))


(comment
  ;;removing an item from a set access pattern
  ;;only set lets you do that in constant time

  (def numbers #{1 2 3 4 5 6})

  (disj numbers 6)

  (disj numbers 10)

  (def numbersv [1 2 3 4 5 6]))


(comment
  ;;splitting a seq access pattern
  ;;only fast on:
  ;;vectors
  (def numbs (vec (range 1000)))

  (count numbs)

  (subvec numbs 800 821))


(comment
  ;;containment check access pattern
  ;;use with set
  ;;and hashmap with key
  (def nums (set (range 1000)))

  (contains? nums 10)

  (contains? {:a 1 :b 2} :b)

  ;;LIFO access pattern read code written by others

  ;;stack
  ;;two uses:
  ;;vectors
  (def todos-two (atom []))

  (defn add-two-todo! [task]
    (swap! todos-two conj task))

  (defn get-todo! []
    (let [[old _new] (swap-vals! todos-two pop)]
      (peek old)))

  (peek [1 2 3 4])
  (pop [1 2 3 4])

      ;;lists
      ;;looks the same with list
  )


(comment
  ;;usage patterns
  ;;common ways of organizing data
  ;;when coding, often encounter same patterns, same
  ;;challenges over and over
  ;;have developed common patterns on how to solve these
  ;;problems
  ;;important to be able to recognize it b/c
  ;;- help you come to a decision quickly when doing data
  ;;modelling 
  ;;- ppl use these all the time so it helps when
  ;;trying to read code written by others

  ;;entity usage patterns
  ;;most common
  ;;hash map
  ;;key words as keys
  ;;values are different types based on w/e is best
  ;;for representing the data - heterogenous
  ;;capturing data about a thing (entity)
  ;;eg. person, user account
  (def eric
    {:name "Eric"
     :address "123 Main St."
     :height 1.6})

  (:name eric)
  (get eric :name)

  (assoc eric :name "joe")

  (assoc eric :user-id "332321")

  (dissoc eric :name)

  ;;variant entity
  ;;1 extra piece (key) of info
  ;;cash
  ;;- amount in $$
  ;;cheque
  ;;- amount in $$
  ;;- account #
  ;;- routing #
  ;;credit card
  ;;- number
  ;;- amount
  ;;- code
  ;;- expiration

  ;;code smell would be replacing payment-method with type
  ;;it is not specific enough and does not properly explain
  ;;what it is
  {:payment-method :cash
   :amount 100}

  {:payment-method :cheque
   :amount 100
   :account "45245"
   :routing "88292829"}

  {:payment-method :credit
   :amount 100
   :number "2938292039"
   :code "322"
   :expiraion "11/23"}

  ;;anti-pattern
  ;;the first 3 entities are interchangeable
  ;;they are equivalent in workflow terms

  ;;this is operating on a different kind of entity
  ;;it is a different workflow
  ;;trying to come up with some place where they have the
  ;;same method, the same database
  ;;trying to shoehorn it into the same function
  {:account-type :admin
   :userid "223213"
   :username "eric"}

  ;;bad, this is the anti-pattern
  (defn save-to-db! [entity]
    (case (or (:payment-method
               :account-type entity))
      :cash
      ;;
      :cheque
      ;;
      :credit
      ;;
      :admin))

  ;;used in different workflows, so can just call the
  ;;right fn
  ;;instead of thinking that:
  ;;they are all being saved so need the same method name
  ;;good
  (defn save-account-to-db! [_entity]
    ...)

  ;;good
  (defn save-pavement-method-to-db! [entity]
    (case (:payment-method entity)
      :cash
      ;;
      :cheque
      ;;
      :credit)))


(comment
  ;;index usage pattern
  ;;uses hashmaps
  (def friends-two [{:name "eric"
                     :phone "72829292"}
                    {:name "kim"
                     :phone "83993282"}
                    {:name "jane"
                     :phone "92839201"}])

  (def rolodexy (into {} (for [friend friends-two]
                           [(:name friend) friend])))

  ;;this is the classic index pattern
  ;;lets you look up some value based on some key
  ;;homogenous key and value 'kinds'
  ;;used to look things up
  {"eric" {:name "eric", :phone "72829292"}
   "kim"  {:name "kim", :phone "83993282"}
   "jane" {:name "jane", :phone "92839201"}}

  ;;can now use this easily to look stuff up
  (get rolodexy "eric")

  ;;accumulator index
  ;;also used to accumulate values given keys
  ;;can use this for many things
  (def visits-three  (atom {}))

  (defn log-visit! [url]
    (swap! visits-three update url (fnil inc 0)))
  ;;
  (def numsy [2 3 2 8 7 3 3 7 5 6 9])

  (reduce (fn [idx n]
            (update idx
                    (if (even? n) :even :odd)
                    (fnil conj []) n))
          {} numsy)

  ;;dispatch table
  ;;for converting a value to a fn

  ;;better
  (def prep-routines {:coffee "brew-coffee"
                      :tea    "make-tea"
                      :bagel  "prepare-bagel"})

  (defn prepare-two [item]
    (let [f (get prep-routines item)]
      (f)))

  ;; not that good
  ;; (defn prepare [item]
    ;; (case item
      ;; :coffee (brew-coffee)
      ;; :tea (make-tea)
      ;; :bagel (prepare-bagel)))

  ;;conversion table
  ;;statically convert one kind of thing to another
  ;;CRUD - way of organizing web api
  ;;- look at web request methods and convert into
  ;;  crud operations
  ;;- web request methods: Create Read Update Delete
  (def op-table {:post   :create
                 :get    :read
                 :put    :update
                 :delete :delete})

  (defn http->crud [method]
    (get op-table method)))


(comment
  ;;tuple usage pattern
  ;;with vectors
  ;;only used when context is super clear
  ;;useful for using right away
  ;;depends on where the value is in the vec, that gives
  ;;it meaning
  ;;code smell - long tuple
  ;;very few uses for it

  ;;variant tuple
  ;;for closed world things where there are a very
  ;;limited number of cases

  (defn get-file-location []
    ...)

  [:url "http://storage.com/my-file.txt"]
  [:disk "/home/eric/my-file.txt"]
  [:paper 5 3 12]

  (defn fetch-file [location]
    (case first location)
    :paper ...
    :disk ...
    :paper))


(comment
  ;;multi-comparison usage pattern
  ;;problem is that it is not in a list
  ;;best you could do is filter
  (defn vp? [name]
    (or (= name "jon k")
        (= name "linda l")
        (= name "fred f")))

  ;;(filter vp? employees)

  ;;question is: is it one of these?
  (def vice-ps #{"jon k"
                 "linda l"
                 "fred f"})

  ;;(filter vice-ps employees)
  ;;using set as a fn

  (#{1 2} 1) ;;returns the arg
  (contains? #{1 2} 1) ;;returns true or false
  )


(comment
  ;;transients
  ;;a way to avoid making useless copies that are not seen
  ;;by anyone
  ;;used when only need to pass on last copy
  (time (persistent! (reduce conj! (transient #{}) (range 10000)))))


(comment
  ;;usage in an atom
  ;;using peek and pop with atoms
  ;;(def queue19 (atom clojure.lang.persistentqueue/empty))

  ;;(defn enqueue19! [value]
  ;;(swap! queue19 conj value)
  ;;nil)

  ;;(defn dequeue19! []
  ;;(let [[old new] (swap-vals! queue19 pop)]
    ;;(peek old)))  
  )


(comment
  ;;hybrid collections
  ;;there isn't a coll that does both of these
  ;;forget duplicates - hashmap, set
  ;;remember order - vector, list, queue
  ;;need to construct the thing that you need

  (def empty-hybrid {:set #{} ;;forget duplicates
                     :vec [] ;;remember order
                     })

  (defn hy-conj [coll value]
    (if (contains? (:set coll) value)
      coll
      (-> coll
          (update :set conj value)
          (update :vec conj value))))

  (defn hy-seq [coll]
    (seq (:vec coll))))


(comment
  ;;vectors and lists in syntax
  ;;in let form there are parens and square brackets, why?
  ;;square are the params for the let, it belongs to the let
  ;;it is the configuration for the let
  ;;it makes it visually distinct
  ;;(let [a 1 b 2] (+ a b))
  ;;or for fn there are square brackets for args
  ;;it belongs to the fn
  ;;(fn [x y]
  ;;(+ x x) (y y))
  )


(comment
  ;;colls vs seqs
  (map inc [1 2 3])
  (update [1 2 3] 1 -)
  ;;why last on map and first on update?

  ;;sequence operations
  ;;arg gets turned into a seq and
  ;;returns a seq
  ;;these are functions over sequences
  ;;where coll comes last
  (map inc [1 2 3])
  (filter even? [1 2 3])
  (take 2 [1 2 3])
  (drop 2 [1 2 3])
  (->> [1 2 3]
       (filter even?))

  ;;awkward, rarely used
  (def s (atom (list 1 2 3 4 5)))
  (swap! s (fn [s] (map inc s)))

  ;;collection operations
  ;;they do a different thing depending on the type
  ;;and some do not work on specific types
  ;;where coll comes first
  (update [1 2 3] 1 -)
  (assoc [1 2 3] 1 4)
  (conj [1 2 3] 4)

  (-> [1 2 3 4 5]
      (update 2 -)
      (assoc 0 :zero)
      (conj 7))

  ;;how it is properly used
  (def a (atom {}))
  (swap! a assoc :key :value))