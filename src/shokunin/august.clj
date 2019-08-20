(ns shokunin.august
  (:use [shokunin.rankable])
  (:require
   [shokunin.facts :as facts]
   [clara.rules :as clara]))

(defrecord DevRankList [devs]
  Rankable
  (rank-for       [_ dev]
    (let [dev-index (.indexOf (:devs _) dev)]
      (if (<= 0 dev-index)
        (+ 1 dev-index)
        (throw (IllegalArgumentException. (str dev " not found in list"))))))
  (first?         [_ dev]     (= 1 (rank-for _ dev)))
  (last?          [_ dev]     (= (count (:devs _)) (rank-for _ dev)))
  (better-than?   [_ from to] (< (rank-for _ from) (rank-for _ to)))
  (neighbours?    [_ from to] (= 1 (gap-between _ from to)))
  (gap-between    [_ from to] (Math/abs (- (rank-for _ from) (rank-for _ to)))))

(defrecord Match [tenx-dev list])

(clara/defquery get-match [] [?match <- Match])

(clara/defrule rules
  [?list <- DevRankList]
  [?list <- DevRankList (not (first? ?list "Jessie"))]
  [?list <- DevRankList (not (last? ?list "Evan"))]
  [?list <- DevRankList (and (not (first? ?list "John")) (not (last? ?list "John")))]
  [:test (better-than? ?list "Sarah" "Evan")]
  [:test (not (neighbours? ?list "Matt" "John"))]
  [:test (not (neighbours? ?list "Evan" "John"))]
  => (clara/insert! (->Match (first (:devs ?list)) (:devs ?list))))

(defn -main
  [& args]
  (-> (clara/mk-session 'shokunin.august)
      (clara/insert-all (map #(->DevRankList %) facts/all-facts))
      (clara/fire-rules)
      (clara/query get-match)
      (println)))
