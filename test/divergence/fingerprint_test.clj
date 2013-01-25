(ns divergence.fingerprint-test
  (:use clojure.test
        divergence.fingerprint))

(testing "extract-fingerprint"
  (deftest resolves-fingerpring-from-request
    (is (= "abcdefg123456"
           (extract-fingerprint {"Authorization" "token abcdefg123456" }))))

  (deftest resolves-empty-string-without-authorization
    (is (= "" (extract-fingerprint {})))))

(testing "expand"
  (def mapping { "abcdefg123456" "https://example.com" })
  (def request {:uri "/api/missions"
                :headers { "Authorization" "token abcdefg123456" }})

  (deftest expand-known-fingerprint
    (is (= '("https://example.com/api/missions") (expand request mapping))))

  (deftest does-not-expand-unknown-fingerprint
    (is (empty? (expand {:uri "/api/missions" :headers {}} mapping)))))
