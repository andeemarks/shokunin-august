(ns shokunin.core (:gen-class)
  (:require [clojure.math.combinatorics :as combo]))

(defn permute
  [devs]
  (map (fn [item] (map-indexed (fn [index subitem] {:dev subitem :pos (+ 1 index)}) item)) (combo/permutations devs))
  )

(defn- valid-position?
  [position solution]
  (and 
    (> position 0) 
    (<= position (count solution))))

(defn- dev-at-position
  [solution position]
  (:dev (nth solution (+ position 1))))

(defn- is-dev-at-position?
  [solution position dev]
  (= dev (dev-at-position solution position)))

(defn pos
  "Return a sequence of all the positions of dev in the solutions sequence"
  [dev solutions]
  (let [dev-positions
    (flatten 
      (map 
        (fn [solution] (filter #(= dev (:dev %)) solution))
        solutions))]
    (distinct (map #(select-keys % [:pos]) dev-positions))))

(defn find2 
  [devs predicate]
  (filter (eval predicate) devs))

(defmulti find (fn [devs dev position & modifier] (or (first modifier) :inclusive)))
(defmethod find :inclusive
  [devs dev position & modifier]
  (filter 
    #(if (valid-position? position %)
      (is-dev-at-position? % position dev)
      false)
    devs))
  
(defmethod find :exclusive
  [devs dev position & modifier]
  (filter 
    #(if (valid-position? position %)
      (not (is-dev-at-position? % position dev))
      false)
    devs))
