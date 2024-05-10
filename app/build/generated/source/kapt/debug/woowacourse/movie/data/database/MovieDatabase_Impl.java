package woowacourse.movie.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import woowacourse.movie.data.dao.MovieDao;
import woowacourse.movie.data.dao.MovieDao_Impl;
import woowacourse.movie.data.dao.MovieReservationDao;
import woowacourse.movie.data.dao.MovieReservationDao_Impl;
import woowacourse.movie.data.dao.MovieTheaterDao;
import woowacourse.movie.data.dao.MovieTheaterDao_Impl;
import woowacourse.movie.data.dao.ScreeningMovieDao;
import woowacourse.movie.data.dao.ScreeningMovieDao_Impl;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MovieDatabase_Impl extends MovieDatabase {
  private volatile MovieDao _movieDao;

  private volatile MovieTheaterDao _movieTheaterDao;

  private volatile MovieReservationDao _movieReservationDao;

  private volatile ScreeningMovieDao _screeningMovieDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `movie_reservation` (`id` INTEGER NOT NULL, `selected_seats` TEXT NOT NULL, `screen_date_time` TEXT NOT NULL, `head_count` INTEGER NOT NULL, `theater_id` INTEGER NOT NULL, `movie_id` INTEGER NOT NULL, `movie_title` TEXT NOT NULL, `movie_description` TEXT NOT NULL, `movie_image_url` TEXT NOT NULL, `movie_start_date` TEXT NOT NULL, `movie_end_date` TEXT NOT NULL, `movie_running_time` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`theater_id`) REFERENCES `movie_theater`(`id`) ON UPDATE CASCADE ON DELETE RESTRICT )");
        db.execSQL("CREATE TABLE IF NOT EXISTS `movie_theater` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `movie` (`id` INTEGER NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `image_url` TEXT NOT NULL, `start_date` TEXT NOT NULL, `end_date` TEXT NOT NULL, `running_time` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `screening_movie` (`id` INTEGER NOT NULL, `screen_date_times` TEXT NOT NULL, `movie_id` INTEGER NOT NULL, `movie_title` TEXT NOT NULL, `movie_description` TEXT NOT NULL, `movie_image_url` TEXT NOT NULL, `movie_start_date` TEXT NOT NULL, `movie_end_date` TEXT NOT NULL, `movie_running_time` INTEGER NOT NULL, `theater_id` INTEGER NOT NULL, `theater_name` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'd6fc53d2da3a4096e886d104c555ca99')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `movie_reservation`");
        db.execSQL("DROP TABLE IF EXISTS `movie_theater`");
        db.execSQL("DROP TABLE IF EXISTS `movie`");
        db.execSQL("DROP TABLE IF EXISTS `screening_movie`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsMovieReservation = new HashMap<String, TableInfo.Column>(12);
        _columnsMovieReservation.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovieReservation.put("selected_seats", new TableInfo.Column("selected_seats", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovieReservation.put("screen_date_time", new TableInfo.Column("screen_date_time", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovieReservation.put("head_count", new TableInfo.Column("head_count", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovieReservation.put("theater_id", new TableInfo.Column("theater_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovieReservation.put("movie_id", new TableInfo.Column("movie_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovieReservation.put("movie_title", new TableInfo.Column("movie_title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovieReservation.put("movie_description", new TableInfo.Column("movie_description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovieReservation.put("movie_image_url", new TableInfo.Column("movie_image_url", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovieReservation.put("movie_start_date", new TableInfo.Column("movie_start_date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovieReservation.put("movie_end_date", new TableInfo.Column("movie_end_date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovieReservation.put("movie_running_time", new TableInfo.Column("movie_running_time", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMovieReservation = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysMovieReservation.add(new TableInfo.ForeignKey("movie_theater", "RESTRICT", "CASCADE", Arrays.asList("theater_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesMovieReservation = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMovieReservation = new TableInfo("movie_reservation", _columnsMovieReservation, _foreignKeysMovieReservation, _indicesMovieReservation);
        final TableInfo _existingMovieReservation = TableInfo.read(db, "movie_reservation");
        if (!_infoMovieReservation.equals(_existingMovieReservation)) {
          return new RoomOpenHelper.ValidationResult(false, "movie_reservation(woowacourse.movie.data.entity.MovieReservation).\n"
                  + " Expected:\n" + _infoMovieReservation + "\n"
                  + " Found:\n" + _existingMovieReservation);
        }
        final HashMap<String, TableInfo.Column> _columnsMovieTheater = new HashMap<String, TableInfo.Column>(2);
        _columnsMovieTheater.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovieTheater.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMovieTheater = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMovieTheater = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMovieTheater = new TableInfo("movie_theater", _columnsMovieTheater, _foreignKeysMovieTheater, _indicesMovieTheater);
        final TableInfo _existingMovieTheater = TableInfo.read(db, "movie_theater");
        if (!_infoMovieTheater.equals(_existingMovieTheater)) {
          return new RoomOpenHelper.ValidationResult(false, "movie_theater(woowacourse.movie.data.entity.MovieTheater).\n"
                  + " Expected:\n" + _infoMovieTheater + "\n"
                  + " Found:\n" + _existingMovieTheater);
        }
        final HashMap<String, TableInfo.Column> _columnsMovie = new HashMap<String, TableInfo.Column>(7);
        _columnsMovie.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovie.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovie.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovie.put("image_url", new TableInfo.Column("image_url", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovie.put("start_date", new TableInfo.Column("start_date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovie.put("end_date", new TableInfo.Column("end_date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMovie.put("running_time", new TableInfo.Column("running_time", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMovie = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMovie = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMovie = new TableInfo("movie", _columnsMovie, _foreignKeysMovie, _indicesMovie);
        final TableInfo _existingMovie = TableInfo.read(db, "movie");
        if (!_infoMovie.equals(_existingMovie)) {
          return new RoomOpenHelper.ValidationResult(false, "movie(woowacourse.movie.data.entity.Movie).\n"
                  + " Expected:\n" + _infoMovie + "\n"
                  + " Found:\n" + _existingMovie);
        }
        final HashMap<String, TableInfo.Column> _columnsScreeningMovie = new HashMap<String, TableInfo.Column>(11);
        _columnsScreeningMovie.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScreeningMovie.put("screen_date_times", new TableInfo.Column("screen_date_times", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScreeningMovie.put("movie_id", new TableInfo.Column("movie_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScreeningMovie.put("movie_title", new TableInfo.Column("movie_title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScreeningMovie.put("movie_description", new TableInfo.Column("movie_description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScreeningMovie.put("movie_image_url", new TableInfo.Column("movie_image_url", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScreeningMovie.put("movie_start_date", new TableInfo.Column("movie_start_date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScreeningMovie.put("movie_end_date", new TableInfo.Column("movie_end_date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScreeningMovie.put("movie_running_time", new TableInfo.Column("movie_running_time", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScreeningMovie.put("theater_id", new TableInfo.Column("theater_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScreeningMovie.put("theater_name", new TableInfo.Column("theater_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysScreeningMovie = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesScreeningMovie = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoScreeningMovie = new TableInfo("screening_movie", _columnsScreeningMovie, _foreignKeysScreeningMovie, _indicesScreeningMovie);
        final TableInfo _existingScreeningMovie = TableInfo.read(db, "screening_movie");
        if (!_infoScreeningMovie.equals(_existingScreeningMovie)) {
          return new RoomOpenHelper.ValidationResult(false, "screening_movie(woowacourse.movie.data.entity.ScreeningMovie).\n"
                  + " Expected:\n" + _infoScreeningMovie + "\n"
                  + " Found:\n" + _existingScreeningMovie);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "d6fc53d2da3a4096e886d104c555ca99", "ceb33766eec7a495132edf9d0aad2237");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "movie_reservation","movie_theater","movie","screening_movie");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `movie_reservation`");
      _db.execSQL("DELETE FROM `movie_theater`");
      _db.execSQL("DELETE FROM `movie`");
      _db.execSQL("DELETE FROM `screening_movie`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(MovieDao.class, MovieDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MovieTheaterDao.class, MovieTheaterDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MovieReservationDao.class, MovieReservationDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ScreeningMovieDao.class, ScreeningMovieDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public MovieDao movieDao() {
    if (_movieDao != null) {
      return _movieDao;
    } else {
      synchronized(this) {
        if(_movieDao == null) {
          _movieDao = new MovieDao_Impl(this);
        }
        return _movieDao;
      }
    }
  }

  @Override
  public MovieTheaterDao movieTheaterDao() {
    if (_movieTheaterDao != null) {
      return _movieTheaterDao;
    } else {
      synchronized(this) {
        if(_movieTheaterDao == null) {
          _movieTheaterDao = new MovieTheaterDao_Impl(this);
        }
        return _movieTheaterDao;
      }
    }
  }

  @Override
  public MovieReservationDao movieReservationDao() {
    if (_movieReservationDao != null) {
      return _movieReservationDao;
    } else {
      synchronized(this) {
        if(_movieReservationDao == null) {
          _movieReservationDao = new MovieReservationDao_Impl(this);
        }
        return _movieReservationDao;
      }
    }
  }

  @Override
  public ScreeningMovieDao screeningMovieDao() {
    if (_screeningMovieDao != null) {
      return _screeningMovieDao;
    } else {
      synchronized(this) {
        if(_screeningMovieDao == null) {
          _screeningMovieDao = new ScreeningMovieDao_Impl(this);
        }
        return _screeningMovieDao;
      }
    }
  }
}
