module.exports = function(grunt) {

  grunt.initConfig({

    // JS TASKS ================================================================
    jshint: {
      all: ['BackEnd/src/main/resources/public/src/js/**/*.js'] 
    },

    uglify: {
      build: {
        files: {
          'BackEnd/src/main/resources/public/dist/js/app.min.js': ['BackEnd/src/main/resources/public/src/js/controllers/*.js','BackEnd/src/main/resources/public/src/js/services/*.js', 'BackEnd/src/main/resources/public/src/js/*.js']
        }
      }
    },

    // CSS TASKS ===============================================================
    less: {
      build: {
        files: {
          'BackEnd/src/main/resources/public/dist/css/style.css': 'BackEnd/src/main/resources/public/src/css/style.less'
        }
      }
    },

    cssmin: {
      build: {
        files: {
          'BackEnd/src/main/resources/public/dist/css/style.min.css': 'BackEnd/src/main/resources/public/dist/css/style.css'
        }
      }
    },

    // COOL TASKS ==============================================================
    watch: {
      css: {
        files: ['BackEnd/src/main/resources/public/src/css/**/*.less'],
        tasks: ['less', 'cssmin']
      },
      js: {
        files: ['BackEnd/src/main/resources/public/src/js/**/*.js'],
        tasks: ['jshint', 'uglify']
      }
    },
    /*
    run: {
      options: {
        // Task-specific options go here. 
      },
      your_target: {
        cmd: './run.sh',
        args: []
      }
    },
    */
    concurrent: {
      options: {
        logConcurrentOutput: true
      },
      tasks: [/*'run:your_target',*/ 'watch']
    }   

  });

  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-less');
  grunt.loadNpmTasks('grunt-contrib-cssmin');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-concurrent');
  //grunt.loadNpmTasks('grunt-run');

  grunt.registerTask('default', ['less', 'cssmin', 'jshint', 'uglify', 'concurrent']);

};