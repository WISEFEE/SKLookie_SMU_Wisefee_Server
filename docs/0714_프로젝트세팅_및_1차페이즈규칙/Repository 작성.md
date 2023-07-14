# 1. 메서드 명명 권장사항
- find, get: 데이터 조회를 위한 메서드를 표현할 때 사용됩니다. 예를 들면 findById, findByUsername, findAll, getByName 등입니다.
- save, create, add: 데이터 생성 또는 업데이트를 위한 메서드를 표현할 때 사용됩니다. 예를 들면 save, create, add 등입니다.
- delete, remove: 데이터 삭제를 위한 메서드를 표현할 때 사용됩니다. 예를 들면 delete, remove, deleteById 등입니다.
- count: 데이터의 개수를 반환하는 메서드를 표현할 때 사용됩니다. 예를 들면 count, countByStatus, countByCategory 등입니다.
- exists, existsBy: 데이터의 존재 여부를 확인하는 메서드를 표현할 때 사용됩니다. 예를 들면 existsById, existsByUsername 등입니다.
- findBy, findAllBy, getBy, findAllBy: 특정 조건에 맞는 데이터를 조회하는 메서드를 표현할 때 사용됩니다. 예를 들면 findByAge, findAllByStatus, getByEmail 등입니다.
- orderBy, sortBy: 데이터의 정렬 순서를 지정하는 메서드를 표현할 때 사용됩니다. 예를 들면 findByCategoryOrderByPriceAsc, findAllByOrderByCreatedAtDesc 등입니다.

# 2. 쿼리 작성 방식
- JQPL(쿼리), JPA Criteria(객체지향), QueryDSL(도전..?)

