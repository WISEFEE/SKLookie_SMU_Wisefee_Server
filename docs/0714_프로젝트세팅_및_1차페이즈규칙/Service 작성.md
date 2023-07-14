# 1. 메서드 명명 권장사항
- get/find: 특정 조건에 맞는 데이터를 조회하는 메서드에 사용됩니다. 예를 들어, getMemberById, findProductsByCategory 등이 있습니다.
- create/add/save: 새로운 데이터를 생성하거나 저장하는 메서드에 사용됩니다. 예를 들어, createMember, addProductToCart, saveOrder 등이 있습니다.
- update/edit/modify: 기존 데이터를 업데이트하거나 수정하는 메서드에 사용됩니다. 예를 들어, updateMember, editProductDetails, modifyOrderStatus 등이 있습니다.
- delete/remove: 데이터를 삭제하는 메서드에 사용됩니다. 예를 들어, deleteMember, removeProductFromCart 등이 있습니다.
- validate/check: 데이터의 유효성을 검증하거나 체크하는 메서드에 사용됩니다. 예를 들어, validateEmail, checkAvailability 등이 있습니다.
- process/handle: 비즈니스 로직을 처리하거나 특정 작업을 수행하는 메서드에 사용됩니다. 예를 들어, processOrder, handlePayment 등이 있습니다.
- calculate/compute: 계산이나 처리를 칙수행하여 결과를 반환하는 메서드에 사용됩니다. 예를 들어, calculateTotalPrice, computeDiscount 등이 있습니다.

# 2. 인터페이스 사용
- 인터페이스 "-Service"와 해당 인터페이스의 구현체인 "-ServiceImpl"을 작성한다.
- 인터페이스와 구현체를 분리하여 코드의 유연성과 확장성을 용이하게 함.
- 각각의 역할과 책임이 명확해지며, 유지보수하기 용이해짐