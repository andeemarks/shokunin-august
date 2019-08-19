(ns shokunin.august
    (:require [shokunin.facts :as facts]
        [clara.rules :refer :all]))

(defprotocol Rankable 
    (rank-for       [_ dev]     "Return the numeric rank for dev" )
    (first?         [_ dev]     "Return true if dev is at rank 1")
    (last?          [_ dev]     "Return true if dev rank is the lowest of all in list" )
    (gap-between    [_ from to] "Return the distance between from and to in terms of rank" )
    (better-than?   [_ from to] "Return true if from has a better/lower rank than to" )
    (neighbours?    [_ from to] "Return true if from and to are only separated by one level of rank" ) )

(defrecord DevRankList [devs] 
    Rankable
    (rank-for       [_ dev]     (+ 1 (.indexOf (:devs _) dev)))
    (first?         [_ dev]     (= 1 (rank-for _ dev)))
    (last?          [_ dev]     (= (count (:devs _)) (rank-for _ dev)))
    (better-than?   [_ from to] (< (rank-for _ from) (rank-for _ to)))
    (neighbours?    [_ from to] (= 1 (gap-between _ from to)))
    (gap-between    [_ from to] (Math/abs (- (rank-for _ from) (rank-for _ to)))) )

(defrecord Match [tenx-dev list])

(defquery get-match [] [?match <- Match])

(defrule rules
    "
    Jessie is not the best developer
    Evan is not the worst developer
    John is not the best developer or the worst developer
    Sarah is a better developer than Evan
    Matt is not directly below or above John as a developer
    John is not directly below or above Evan as a developer
    "
    [?list <- DevRankList]
    [?list <- DevRankList (not (first? ?list "Jessie"))]
    [?list <- DevRankList (not (last? ?list "Evan"))]
    [?list <- DevRankList (and (not (first? ?list "John")) (not (last? ?list "John")))]
    [:test (better-than? ?list "Sarah" "Evan")]
    [:test (not (neighbours? ?list "Matt" "John"))]
    [:test (not (neighbours? ?list "Evan" "John"))]
    => (insert! (->Match (first (:devs ?list)) (:devs ?list))))

(defn -main
    [& args]
    (-> (mk-session 'shokunin.august)
        (insert-all (map #(->DevRankList %) facts/all-facts))
        (fire-rules)
        (query get-match)
        (println)) )
