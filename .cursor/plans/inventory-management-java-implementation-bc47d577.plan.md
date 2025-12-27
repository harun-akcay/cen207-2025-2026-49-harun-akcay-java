<!-- bc47d577-9f6c-4336-a2de-bae9f1217023 3de007f1-e547-4843-ae5a-ae0b10e515f2 -->
# Inventory Management Java Implementation Plan

## Genel Yapı

### Paket Yapısı

- `com.hakcay.inventorymanagement.material` - Material Inventory modülü
- `com.hakcay.inventorymanagement.project` - Project Tracking modülü  
- `com.hakcay.inventorymanagement.expense` - Expense Logging modülü
- `com.hakcay.inventorymanagement.sales` - Sales Tracker modülü
- `com.hakcay.inventorymanagement.algorithms` - 12 algoritma/data structure (ayrı paketler)
- `doublylinkedlist` - Double Linked List
- `xorlinkedlist` - XOR Linked List
- `sparsematrix` - Sparse Matrix
- `stackqueue` - Stack ve Queue
- `heap` - Heap ve Heap Sort
- `graph` - BFS/DFS ve Strongly Connected Components
- `hashtable` - Hash Tables
- `kmp` - KMP Algorithm
- `huffman` - Huffman Coding
- `bplustree` - B+ Tree
- `fileops` - File Operations

## Algoritma Entegrasyon Stratejisi

1. **Double Linked List** → Material history tracking (ekleme/silme geçmişi)
2. **XOR Linked List** → Memory-efficient material list (büyük envanterler için)
3. **Sparse Matrix** → Warehouse layout/material grid representation
4. **Stack/Queue** → Undo/redo operations ve task queue
5. **Heap** → Priority-based material ordering (stok seviyesine göre)
6. **BFS/DFS** → Project dependency graph traversal
7. **Hash Tables** → Fast material/project lookup (O(1) erişim)
8. **Strongly Connected Components** → Project dependency cycle detection
9. **KMP Algorithm** → Material/project name search
10. **Huffman Coding** → Data compression for backup/storage
11. **B+ Tree** → File indexing for large datasets
12. **File Operations** → Persistent storage (hash-based file operations)

## Implementation Sırası (Incremental Approach)

**Her modül/algoritma için: Implement → Test → Coverage Kontrol → Doğrula → Sonraki**

### Faz 1: Material Inventory Modülü (İlk Adım)

1. Material model class implement et
2. MaterialRepository (in-memory) implement et
3. MaterialService implement et
4. MaterialServiceTest yaz (mevcut testleri geçecek şekilde)
5. **Test çalıştır** (`mvn test`)
6. **Jacoco raporu kontrol et** (`mvn jacoco:report`)
7. Coverage %100'e yakın değilse eksik testleri ekle
8. **Tekrar test çalıştır ve doğrula**

### Faz 2: Project Tracking Modülü

9. Project model class implement et
10. ProjectRepository (in-memory) implement et
11. ProjectService implement et
12. ProjectServiceTest yaz (mevcut testleri geçecek şekilde)
13. **Test çalıştır ve coverage kontrol et**
14. **Jacoco raporu kontrol et**
15. Coverage %100'e yakın değilse eksik testleri ekle
16. **Tekrar test çalıştır ve doğrula**

### Faz 3: Expense Logging Modülü

17. Expense model class implement et
18. ExpenseRepository (in-memory) implement et
19. ExpenseService implement et
20. ExpenseServiceTest yaz (mevcut testleri geçecek şekilde)
21. **Test çalıştır ve coverage kontrol et**
22. **Jacoco raporu kontrol et**
23. Coverage %100'e yakın değilse eksik testleri ekle
24. **Tekrar test çalıştır ve doğrula**

### Faz 4: Algoritma Implementasyonları

**Her algoritma için: Implement → Test → Coverage → Doğrula → Sonraki**

25-28. Hash Tables (implement + test + coverage + doğrula)
29-32. Stack/Queue (implement + test + coverage + doğrula)
33-36. Double Linked List (implement + test + coverage + doğrula)
37-40. Heap (implement + test + coverage + doğrula)
41-44. Sparse Matrix (implement + test + coverage + doğrula)
45-48. XOR Linked List (implement + test + coverage + doğrula)
49-52. BFS/DFS (implement + test + coverage + doğrula)
53-56. Strongly Connected Components (implement + test + coverage + doğrula)
57-60. KMP Algorithm (implement + test + coverage + doğrula)
61-64. Huffman Coding (implement + test + coverage + doğrula)
65-68. B+ Tree (implement + test + coverage + doğrula)
69-72. File Operations (implement + test + coverage + doğrula)

### Faz 5: Algoritma Entegrasyonları

**Her entegrasyon için: Entegre et → Integration test → Coverage → Doğrula**

73-76. Hash Tables → Material/Project lookup entegrasyonu
77-80. Stack/Queue → Undo/redo entegrasyonu
81-84. Double Linked List → Material history entegrasyonu
85-88. Heap → Priority queue entegrasyonu
89-92. Sparse Matrix → Warehouse layout entegrasyonu
93-96. XOR Linked List → Memory-efficient list entegrasyonu
97-100. BFS/DFS → Project dependency entegrasyonu
101-104. SCC → Cycle detection entegrasyonu
105-108. KMP → Search functionality entegrasyonu
109-112. Huffman → Compression entegrasyonu
113-116. B+ Tree → File indexing entegrasyonu
117-120. File Operations → Persistent storage entegrasyonu

### Faz 6: Sales Tracker ve Ana Uygulama

121-124. Sales Tracker modülü (implement + test + coverage + doğrula)
125-128. InventorymanagementApp main method (console menu + test + coverage + doğrula)

### Faz 7: Final Kontroller

129. Tüm testleri çalıştır (`mvn test`)
130. Jacoco coverage raporu oluştur (`mvn jacoco:report`)
131. Coverage %100'e yakın değilse eksik testleri ekle
132. Javadoc dokümantasyonu ekle (%100 coverage)
133. Final doğrulama

## Teknik Detaylar

### pom.xml Güncellemeleri

- JUnit 4.13.2 (mevcut - değişmeyecek)
- Jacoco plugin aktif edilecek ve %100 coverage check eklenecek
- Maven compiler Java 1.8

### Test Stratejisi (Incremental)

- **Her modül/algoritma implement edildikten hemen sonra testleri yaz**
- **Testleri çalıştır ve coverage kontrol et**
- **Coverage %100'e yakın değilse eksik testleri ekle**
- **Sonraki modüle geçmeden önce mevcut modülün testleri geçmeli**
- Her algoritma için unit testler
- Her servis için comprehensive testler
- Edge case'ler ve boundary testler
- Integration testler (algoritma entegrasyonları için)

### Coverage Hedefi

- Line Coverage: %100
- Branch Coverage: %100
- Method Coverage: %100
- Class Coverage: %100

### To-dos

- [x] Material Inventory modülünü implement et: Material model, MaterialRepository, MaterialService ve testleri
- [x] Project Tracking modülünü implement et: Project model, ProjectRepository, ProjectService ve testleri
- [x] Expense Logging modülünü implement et: Expense model, ExpenseRepository, ExpenseService ve testleri
- [x] Hash Tables algoritmasını implement et ve Material/Project lookup için entegre et
- [x] Stack ve Queue algoritmalarını implement et ve undo/redo için entegre et
- [x] Double Linked List algoritmasını implement et ve Material history için entegre et
- [x] Heap ve Heap Sort algoritmalarını implement et ve priority queue için entegre et
- [x] Sparse Matrix algoritmasını implement et ve warehouse layout için entegre et
- [x] XOR Linked List algoritmasını implement et ve memory-efficient list için entegre et
- [x] BFS/DFS algoritmalarını implement et ve project dependency graph için entegre et
- [x] Strongly Connected Components algoritmasını implement et ve cycle detection için entegre et
- [x] KMP Algorithm implement et ve search functionality için entegre et
- [x] Huffman Coding algoritmasını implement et ve compression için entegre et
- [x] B+ Tree algoritmasını implement et ve file indexing için entegre et
- [ ] File Operations algoritmasını implement et ve persistent storage için entegre et
- [ ] InventorymanagementApp main method ve console menu implement et
- [ ] Sales Tracker modülünü implement et: Sale model, SaleService ve testleri
- [ ] Jacoco coverage check konfigürasyonunu aktif et ve %100 coverage hedefi ayarla
- [ ] Tüm sınıflar için Javadoc dokümantasyonu ekle (%100 coverage)