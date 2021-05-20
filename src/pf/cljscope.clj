(ns pf.cljscope)

(comment
  ;;global scope or namespaces
  ;;static or lexical scope - clear definitions
  ;;all code in clj is run in the context of a ns
  ;;the ns in which something is defined, determines vars
  ;;and names it has access to
  ;;with require statements - now can access any var
  ;;in that ns that you required
  ;;works top down, starts at top and goes down with defs
  
  ;;when do def, adding something to the global scope
  (def x "my name is x")
  (defn f [])
  (defonce p "p")
  )


(comment
  ;;let and fn arg scope
  ;;static or lexical scope
  ;;have to know what scope you are in to know the
  ;;value of an expression
  ;;can access x and y within body of let
  ;;parens end the expression and things are not
  ;;available anymore - have a lot of control over scope
  (let [x 1
        y 2]
    (+ x y)
    )
  
  ;;this is called shadowing - when a var is already
  ;;defined higher up but you define it in a let expression
  ;;below it
  ;;(let [_x 10]
    ;;(let [x 1
      ;;    y 2]
      ;;(+ x y)))
  
  ;;z has access to all of the vars defined above it in
  ;;the let
  ;;can only look up, scope can only look up, z cannot
  ;;be above x and y
  (let [x 1
        y 2
        z (+ x y)]
    z)
  
  ;;fn arg scope
  ;;args are available within whole body of fn
  ;;acts like let but cannot control what values
  ;;are assigned
  (defn my-fn [a b c]
    (+ a b c))
  )

(comment
  ;;dynamic scope
  ;;based on call stack not on just reading it
  )


(comment
  ;;scope problems diagnosed
  ;;problem 1
  ;;when have let binding, it is immutable
  ;;def inside is referring to a different variable, the
  ;;global scope, so it is like there is (def x 0) above
  ;;the let - even though it has the same name
  (let [x 0]
    (while (< x 10)
      (println x)
      (def x (inc x))))
  ;;solutions
  (dotimes [x 10]
    (println x))

  (let [x (atom 0)]
    (while (< @x 10)
      (println @x)
      (swap! x inc)))
  
  (loop [x 0]
    (when (< x 10)
      (println x)
      (recur (inc x))))
  
  ;;problem 2
  ;;its taking the global map and using it as a local
  ;;variable
  (defn double-values [map]
    (into {} (map (fn [[k v]]
                    [k (* 2 v)]) map)))
  
  ;;solutions
  ;;re-name the arg
    (defn double-values-two [mp]
      (into {} (map (fn [[k v]]
                      [k (* 2 v)]) mp)))
  )