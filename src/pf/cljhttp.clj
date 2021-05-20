(ns pf.cljhttp
  (:require [clj-http.client :as http]
            [clj-http.conn-mgr :as conn]))

(def response-lisp (http/get "http://lispcast.com"
                        {:debug? true}))

(:status response-lisp)
(:headers response-lisp)
(type (:body response-lisp))

;;sending and receiving data
(def response (http/get "http://api.weatherbit.io/v2.0/current?city=Toronto&country=CA&key=bb182734a3e040d3844fa1658480c29c"
                        {:as :json-string-keys})) ;; returns keys as strings
;; {:as :json} -> returns json
;; {:as :clojure} -> returns edn
(:status response)
(:body response)

;;get does not have a body when you send a request, but posts do
(def response-two (http/post "http://api.weatherbit.io/v2.0/current?city=Tor onto&country=CA&key=bb182734a3e040d3844fa1658480c29c"
                             {:as :json ;;returns json
                              :debug? true
                              :debug-body? true
                              :content-type :json
                              :form-params {:name "Lisa"}})) ;;want to send json


;;options rundown
;;timeout - making request to another server, don't want it to take too long
(http/get "http://lispcast.com"
          {:conn-timeout 1000 ;;how long it takes to make the connection
           :socket-timeout 1000}) ;;socket is open but no receivng any data

;;exceptions - handles when and if exceptions are thrown
(http/get "http://lispcast.com/fdkdkfjkdokd"
          {:conn-timeout 1000
           :socket-timeout 1000
           :throw-exceptions false})

;;basic auth
(http/get "http://lispcast.com"
          {:basic-auth ["username" "password"]})

;;redirects
(http/get "http://lispcast.com"
          {:max-redirects 0})

;;ssl insecurity - allows insecure connection
(http/get "http://lispcast.com"
          {:insecure? true})

;;connection pool - only need to cconnect once and then can make multiple
;;requests from that
(def connection-pool (conn/make-reusable-conn-manager
                      {:threads 4 :timeout 4}))
(dotimes [_x 10]
  (http/get "http://lispcast.com" {:connection-manager connection-pool}))

(conn/shutdown-manager connection-pool)
