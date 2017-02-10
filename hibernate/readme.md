
1)how to detect child object change from parent class in hibernate.
===================================================================
      public class HistoryInterceptor extends EmptyInterceptor {

  private static Logger logger = LoggerFactory.getLogger(HistoryInterceptor.class);
  
  /**
   * Called when an object is detected to be dirty, during a flush. The interceptor may modify the detected
   * <tt>currentState</tt>, which will be propagated to both the database and the persistent object.
   * Note that not all flushes end in actual synchronization with the database, in which case the
   * new <tt>currentState</tt> will be propagated to the object, but not necessarily (immediately) to
   * the database. It is strongly recommended that the interceptor <b>not</b> modify the <tt>previousState</tt>.
   *
   * @return <tt>true</tt> if the user modified the <tt>currentState</tt> in any way.
   */
  @Override
  public boolean onFlushDirty(final Object pEntity,
                              final Serializable pId,
                              final Object[] pCurrentState,
                              final Object[] pPreviousState,
                              final String[] pPropertyNames,
                              final Type[] pTypes) throws CallbackException {
    // there must no exception be uncaught here!
    try { 
.....
http://prismoskills.appspot.com/lessons/Hibernate/Chapter_20_-_Dirty_checking.jsp
Changes to persistent instances are detected at flush time.

2)N+1 problem in hibernate
===========================

Resolve N+1 SELECTs problem


Okay, so you want to print out all the details of phone models. A naive O/R implementation would SELECT all mobile vendors and then do N additional SELECTs for getting the information of PhoneModel for each vendor.

-- Get all Mobile Vendors
 SELECT * FROM MobileVendor;
-- For each MobileVendor, get PhoneModel details
 SELECT * FROM PhoneModel WHERE MobileVendor.vendorId=?
(i) HQL fetch join

"from MobileVendor mobileVendor join fetch mobileVendor.phoneModel PhoneModels"

3)hibernate 2nd level cache:
=============================
<property name="cache.provider_class">org.hibernate.cache.EhCacheProvider</property>  
<property name="hibernate.cache.use_second_level_cache">true</property>  
<cache usage="read-only" />  
3) Create ehcache.xml file

<?xml version="1.0"?>  
<ehcache>  
  
<defaultCache   
maxElementsInMemory="100"   
eternal="true"/>  
  
</ehcache>  

