(ns shokunin.core (:gen-class)
  (:require [clojure.math.combinatorics :as combo]))

(defn permute
  [devs]
  (map (fn [item] (map-indexed (fn [index subitem] {:dev subitem :pos (+ 1 index)}) item)) (combo/permutations devs))
  )
