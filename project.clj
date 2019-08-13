(defproject app "0.1.0-SNAPSHOT"
  :description "Shokunin August Challenge"
  :url "https://github.com/andeemarks/shokunin-august"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main shokunin.rules
  :dependencies [[org.clojure/math.combinatorics "0.1.6"]
                  [com.cerner/clara-rules "0.19.0"]
                  [org.clojure/clojure "1.10.1"]])
